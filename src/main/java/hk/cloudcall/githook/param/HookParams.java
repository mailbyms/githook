package hk.cloudcall.githook.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * BcryptParams class
 *
 * @author MIKE
 * @date 20210803
 */
@Setter
@Getter
@ToString
public class HookParams {
    private String object_kind;
    private String ref;
    private String checkout_sha;

    private String user_name;
    private int user_id;
    private String user_email;
    private int total_commits_count;

    @Setter
    @Getter
    @ToString
    public class repository {
        private String name;
        private String url;
        private String git_ssh_url;
        private String git_http_url;
        private int visibility_level;
        private String homepage;
        private String description;
    }

    private repository repository;
}
