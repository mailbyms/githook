package hk.cloudcall.githook.controller;

import hk.cloudcall.githook.param.BaseResult;
import hk.cloudcall.githook.param.HookParams;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/hook")
public class ApiController {
    String command = "/data/gitsync.sh";

    // 运行命令，抓取控制台输出
    private void runSync(String name, String url) throws Exception {
        command += " " + name + " " + url;
        System.out.println("command:" + command);
        Process p = Runtime.getRuntime().exec(command);

        InputStream is = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        p.waitFor();
        System.out.println("运行结果状态码：" + p.exitValue());
        if (p.exitValue() != 0) {
            //说明命令执行失败
            //可以进入到错误处理步骤中
            System.out.println("执行同步错误");
        }

        String s = null;
        while ((s = reader.readLine()) != null) {
            System.out.println(s);
        }
        reader.close();
    }

    @PostMapping("/alicode")
    public BaseResult match(@RequestBody @Validated HookParams hp) throws Exception {
        System.out.println(hp);

        runSync(hp.getRepository().getName(), hp.getRepository().getUrl());

        BaseResult result = new BaseResult();
        result.setSuccess(true);


        return result;
    }

}
