package ui;

import domain.User;

import java.util.ArrayList;
import java.util.Random;
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
        Scanner sc = new Scanner(System.in);
        //判断用户名
        //不存在：
        //存在：禁用
        //存在，验证验证码
        //存在，验证密码是否正确(三次)
        System.out.println("请输入用户名");
        //用户名如果未注册提示：用户名未注册，请先注册
        //用户被锁定提示：用户xxx已经锁定，请联系黑马程序员官方客服：XXX-XXXXX
        String username = sc.next();
        if(!containsUsername(username,users)){
            System.out.println("当前用户名"+username+"未注册");
            //用户名不存在就退出登录 选择注册
            return;
        }
        int index = findIndex(username,users);
        User user = users.get(index);
        if(!user.isStatus()){
            System.out.println("用户"+username+"已经锁定，请联系黑马程序员官方客服：XXX-XXXXX");
            return;
        }

        for (int i=0;i<3;i++) {
            System.out.println("请输入密码");
            String password = sc.next();

            while (true) {
                String rightcode=getCode();
                System.out.println("验证码为:"+rightcode);
                System.out.println("请输入验证码");
                String code= sc.next();
                if(code.equalsIgnoreCase(rightcode)){
                    break;
                }else{
                    System.out.println("验证码输入错误");
                }
            }
            //验证密码是否正确
            if(password.equals(user.getPassword())){
                System.out.println("登录成功");
                FightingGame game = new FightingGame();
                game.gameStart(username);

                break;
            }else{
                if(i==2){
                    user.setStatus(false);
                    System.out.println("用户"+username+"已经锁定，请联系黑马程序员官方客服：XXX-XXXXX");
                }else{
                    System.out.println("密码错误，还剩余"+(2-i)+"次机会");
                }
            }

        }




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

    public String getCode(){
        // 长度为5
        //由4位大写或者小写字母和1位数字组成，同一个字母可重复
        //数字可以出现在任意位置
        ArrayList<Character> chars = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            chars.add((char)('a'+i));
            chars.add((char)('A'+i));
        }
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(chars.get(new Random().nextInt(chars.size())));
        }
        sb.append(new Random().nextInt(10));
        char[] charArr = sb.toString().toCharArray();
        int index=new Random().nextInt(charArr.length);
        char temp=charArr[index];
        charArr[index]=charArr[charArr.length-1];
        charArr[charArr.length-1]=temp;
        return new String(charArr);
    }

    public int findIndex(String username,ArrayList<User> users){
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getUsername().equals(username)){
                return i;
            }
        }
        return -1;
    }

}
