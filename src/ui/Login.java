package ui;

import domain.User;

import java.util.ArrayList;
import java.util.Scanner;

public class Login {
    public void start(){
        System.out.println("打开了登录界面");
        ArrayList<User> users = new ArrayList<>();


        while (true) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("请选择操作：1登录 2注册 3退出");

            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch (choice){
                case 1 -> login(users);
                case 2 -> register(users);
                case 3 -> {
                    System.out.println("用户选择了退出");
                    System.exit(0);
                }
                default -> System.out.println("输入有误，请重新输入");
            }
        }
    }

    public void login(ArrayList<User> users){
        System.out.println("用户选择了登录");
    }

    public void register(ArrayList<User> users){
        System.out.println("用户选择了注册");
        User newUser = new User();
        Scanner sc = new Scanner(System.in);
        //用户名
        while (true) {
            System.out.println("请输入用户名");
            String username = sc.next();
            //用户名唯一
            //长度必须在3 ~ 16位
            //只能由字母、数字组成，不能是纯数字
            //先验证格式 再验证唯一   先判断异常的数据 再判断正常的
            if(!checkLen(username,3,16)){
                System.out.println("用户名长度必须在3 ~ 16位");
                continue;
            }
            if(!checkUsername(username)){
                System.out.println("用户名只能由字母、数字组成，不能是纯数字");
                continue;
            }

            if(containsUsername(username,users)){
                System.out.println("用户名重复");
                continue;
            }
            newUser.setUsername(username);
            break;
        }
        //密码
        while (true) {
            System.out.println("请输入密码");
            //长度3 ~ 8位
            //只能是字母加数字的组合，不能有其他字母
            String password1 = sc.next();
            if(!checkLen(password1,3,8)){
                System.out.println("密码长度需为3 ~ 8位");
                continue;
            }
            if(!checkPassword(password1)){
                System.out.println("只能是字母加数字的组合，不能有其他字符");
                continue;
            }
            System.out.println("请再次输入密码");
            String password2 = sc.next();
            if(!password1.equals(password2)){
                System.out.println("两次密码输入不一致");
                continue;
            }
            newUser.setPassword(password1);
            break;
        }
        users.add(newUser);
        System.out.println("当前用户注册成功");

    }

    public boolean checkLen(String str,int minlen,int maxlen){
        return minlen<=str.length()&&str.length()<=maxlen;
    }

    public boolean checkUsername(String username){
        int[] arr = getCount(username);
        return arr[0]>0 && arr[1]>=0 && arr[2]==0;
    }

    public boolean containsUsername(String username,ArrayList<User> users){
        for (int i = 0; i < users.size(); i++) {
            if(username.equals(users.get(i).getUsername())){
                return true;
            }
        }
        return false;
    }

    public boolean checkPassword(String  password){
        int[]arr = getCount(password);
        return arr[0]>0 && arr[1]>0 && arr[2]==0;
    }


    public int[] getCount(String str){
        int charcount=0,numcount=0,othercount=0;
        for(int i=0;i<str.length();i++){
            if((str.charAt(i)>='a'&&str.charAt(i)<='z')||(str.charAt(i)>='A'&&str.charAt(i)<='Z')){
                charcount++;
            }else if(str.charAt(i)>='0'&&str.charAt(i)<='9'){
                numcount++;
            }
            else{
                othercount++;
            }
        }
        return new int[]{charcount,numcount,othercount};
    }


}
