package com.example.demo.rest;

import lombok.Data;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloService {
    /**
     *  http://localhost:8080/hello?name=sabo&password=123
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam String name, @RequestParam String password) {
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(name, password);
            token.setRememberMe(true);
            try {
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                System.out.println("There is no user with username of " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                System.out.println("Password for account " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {
                System.out.println("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
                System.out.println("==========xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            }
        } else {
            System.out.println("=============");
        }

        return "Hello world: " + name;
    }

    /**
     * http://localhost:8080/hello/rick
     *
     * @param name
     * @return
     */
    @RequiresPermissions("document:read")
    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    public String helloViaPath(@PathVariable String name) {
        return "Hello world: " + name;
    }

    /**
     * curl --location --request POST 'http://127.0.0.1:8080/hello' \
     * --header 'Content-Type: application/json' \
     * --data-raw '{
     * 	"name": "sabo"
     * }'
     *
     * @param requstBean
     * @return
     */
    @RequiresRoles("admin")
    @PostMapping("/hello")
    public String helloViaBody(@RequestBody RequstBean requstBean) {
        return "hello world: " + requstBean.getName();
    }

    @Data
    private static class RequstBean {
        private String name;
    }
}
