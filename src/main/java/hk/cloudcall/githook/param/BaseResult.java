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
public class BaseResult {

    private boolean success;

    private String message;

}
