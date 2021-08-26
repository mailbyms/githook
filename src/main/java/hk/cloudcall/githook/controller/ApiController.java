package hk.cloudcall.githook.controller;

import hk.cloudcall.githook.param.BaseResult;
import hk.cloudcall.githook.param.HookParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hook")
public class ApiController {
    private final static Logger logger = LoggerFactory.getLogger(ApiController.class);
    String shell = "/data/githook/gitsync.sh";

    // 运行命令，抓取控制台输出
    private void runSync(String name, String url) throws Exception {
        String command = shell + " " + name + " " + url;
        logger.info("command:{}", command);
        Process p = Runtime.getRuntime().exec(command);

        /* 不需要 p.waitFor 阻塞。因为 webhook 供供是通知作用，不需要等处理完成（可能会超时）
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
        */
    }

    @PostMapping("/alicode")
    public BaseResult match(@RequestBody @Validated HookParams hp) throws Exception {
        logger.info("Callback params: {}", hp);
        String repoName = hp.getRepository().getName();
        String repoUrl = hp.getRepository().getUrl();

        runSync(repoName, repoUrl);

        BaseResult result = new BaseResult();
        result.setSuccess(true);


        return result;
    }

}

