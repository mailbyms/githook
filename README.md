## 项目逻辑

 阿里云code 代码仓库的 webhook 传递的参数：

```json
{
   "after" : "27802866fa33860f54c610982f785d40a22190ce",
   "before" : "27802866fa33860f54c610982f785d40a22190ce",
   "checkout_sha" : "27802866fa33860f54c610982f785d40a22190ce",
   "commits" : [
      {
         "author" : {
            "email" : "mike@abc.com",
            "name" : "mike"
         },
         "id" : "27802866fa33860f54c610982f785d40a22190ce",
         "message" : "t push -u origin master\n",
         "timestamp" : "2021-08-05T14:33:10+08:00",
         "url" : "https://code.aliyun.com/janeyre_1983/spring-config/commit/27802866fa33860f54c610982f785d40a22190ce"
      }
   ],
   "crp_user_identity" : "1177988989466961",
   "message" : null,
   "object_kind" : "push",
   "project_id" : 629653,
   "ref" : "refs/heads/master",
   "repository" : {
      "description" : "",
      "git_http_url" : "https://code.aliyun.com/janeyre_1983/spring-config.git",
      "git_ssh_url" : "git@code.aliyun.com:janeyre_1983/spring-config.git",
      "homepage" : "https://code.aliyun.com/janeyre_1983/spring-config",
      "name" : "spring-config",
      "url" : "git@code.aliyun.com:janeyre_1983/spring-config.git",
      "visibility_level" : 0
   },
   "total_commits_count" : 1,
   "user_email" : "janeyre_1983@abc.com",
   "user_id" : 362373,
   "user_name" : "mike"
}
```

本项目分析阿里云code回调参数后，会调用

```shell
/data/gitsync.sh spring-config git@code.aliyun.com:janeyre_1983/spring-config.git
```

其中  `gitsync.sh` 见项目目录

**注意：同步的2个 git 服务器，都需要预先设置 ssh 登录**



## 参考

来源：https://www.opentechguides.com/how-to/article/git/177/git-sync-repos.html

The first step is to use the **git clone** command to copy the content of the primary repo to a folder in your local computer.

```
# git clone --mirror https://primary_repo_url/primary_repo.git
```

A folder that is initialised as a git repository contains all your project files, called the working tree, and also contains a subdirectory named `.git` that contains the revision history of your files. A repository that contains only the revision history and no working tree is called a **bare repo**. The --mirror option is used to create a bare repo that maps all the branches and refs in the source(primary repo) with the branches and refs in the target(local repo).

The git clone command above will create a directory with the name of your repo. Change to that directory and add a remote for the secondary repo (repo in Bitbucket)

```
# cd primary_repo.git
# git remote add --mirror=fetch secondary https://secondary_repo_url/secondary_repo.git
```

Run **git fetch** command to get the commits and refs from the primary repo (origin) to your local repo.

```
# git fetch origin
```

Finally run **git push** to update the secondary repo using local refs.

```
# git push secondary --all
```

The secondary repo will now have all the files and their revision history same as in the primary repo.

After this, whenever a commit is made on the primary repo, run the git fetch followed by git push commands to keep the two repos in sync.

```
# git fetch origin
# git push secondary --all
```

