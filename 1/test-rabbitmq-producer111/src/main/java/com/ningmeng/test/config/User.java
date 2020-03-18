package com.ningmeng.test.config;

/**
 * Created by 周周 on 2020/3/17.
 */
public class User {

   /* private static void Main(String[] args){
        String aa="n";
        do{
            //进行一个目录进行抽奖  点击按钮
            System.out.println("幸运大抽奖");
            System.out.println("..........................");
            //这是一个接受逇菜单
            Scanner scanner = new Scanner(System.in);

            break;
        }while (aa.equals("y")){
            System.out.println("下次继续");
        }
    }*/



    /*public class LuckDraw {
        //记录输入卡号注册
        static int number = 0;
        public static void lukcDraw() {
            //是否注册 没注册不能抽
            if(User.userName.length() ==0 ) {
                //没注册 下面都不执行
                System.out.println("你没注册");
                return;
            }
            //是否登录 没登录不让抽
            if(!User.isLogin) {
                System.out.println("你没登录");
                return;
            }
            //如果卡号没输对 也不让抽奖 直接return
            if (!isLuckNumber()) {
                System.out.println("卡号不对！不能登录");
                return;
            }
            //真的 可以抽奖了
            //定义一个 变量保存中奖的结果
            boolean isLucky = false;
            //随机五个数 打印出结果 并判断是否匹配幸运号码
            //定义一个变量 来保存累加的字符串
            String s = "本次幸运的数";
            for (int i = 0; i < 5; i++) {
                int num = (int) (Math.random() * (2000-1000+1) + 1000);
                //拼接要打印的字符串
                if(i<5) {
                    s = s + num+",";
                }else {
                    s = s+num;
                }
                //判断是否中奖
                if(User.luckNumber==num);
                //中奖
                isLucky = true;
            }
            //打印幸运数字
            System.out.println(s);
            if (isLucky) {
                System.out.println("你中奖了");
            }else {
                System.out.println("你没中奖");
            }
        }
        public static boolean isLuckNumber() { //判断卡号是个输入正确
            //抽奖
            System.out.println("请输入卡号");
            Scanner scanner = new Scanner(System.in);
            int luckNumber = scanner.nextInt();
            //判断用户输入的卡号是不是 这个用户之前随机出来的那个数
            if (luckNumber==User.luckNumber) {
                System.out.println("输入正确抽奖马上运行");
                return true;
                //卡号相同可以抽奖
            }else {
                //卡号不相同 重新输入三次机会
                number++;
                if (number!=3) {
                    isLuckNumber();
                }else {
                    System.out.println("三次机会已用尽");
                }
                return false;
            }
        }
    }*/
}
