package app.util.normal;

/**
 * Created by JinXinyi on 2017/8/4.
 */

public class OperatorUtil {
    private static String CMCC = "移动";
    private static String UNICOM = "联通";
    private static String TELECOM = "电信";
    private static String UNKNOWN = "未知";

    /*
     * 判断输入的是否为数字
     *
     * @返回true说明是数字，false说明不全是数字
     */
    private static boolean isNum(String phoneNum) {
        for (int i = 0; i < phoneNum.length(); i++) {
            if (!Character.isDigit(phoneNum.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /*
     * 判断运营商
     */
    public static String execute(String phone) {
        String head1 = "";
        String head2 = "";

        // 去除前后的空白
        phone = phone.trim();

        // 判断输入的电话号码是否合法
        if (phone == null || phone.length() < 11) {
            System.out.println("length<11");
            return UNKNOWN;
        } else {
            // 处理国内的+86开头
            if (phone.startsWith("+")) {
                phone = phone.substring(1);
            }
            if (phone.startsWith("86")) {
                phone = phone.substring(2);
            }
        }
        // 去除+86后电话号码应为11位
        if (phone.length() != 11) {
            System.out.println("not = 11");
            return UNKNOWN;
        }
        // 判断去除+86后剩余的是否全为数字
        if (!isNum(phone)) {
            System.out.println(" not num");
            return UNKNOWN;
        }
        // 截取前3或前4位电话号码，判断运营商
        head1 = phone.substring(0, 3);
        head2 = phone.substring(0, 4);

        // 移动前三位
        boolean cmcctemp3 = head1.equals("134") || head1.equals("135") || head1.equals("136")
                || head1.equals("137") || head1.equals("138")
                || head1.equals("139") || head1.equals("147")
                || head1.equals("150") || head1.equals("151")
                || head1.equals("152") || head1.equals("157")
                || head1.equals("158") || head1.equals("159")
                || head1.equals("182") || head1.equals("183")
                || head1.equals("184") || head1.equals("178")
                || head1.equals("187") || head1.equals("188");
        if (cmcctemp3) {
            return CMCC;
        }
        // 移动前4位
        boolean cmcctemp4 = head2.equals("1340") || head2.equals("1341")
                || head2.equals("1342") || head2.equals("1343")
                || head2.equals("1344") || head2.equals("1345")
                || head2.equals("1346") || head2.equals("1347")
                || head2.equals("1348") || head2.equals("1705");
        if (cmcctemp4) {
            return CMCC;
        }
        // 联通前3位
        boolean unicomtemp = head1.equals("130") || head1.equals("131")
                || head1.equals("132") || head1.equals("145")
                || head1.equals("155") || head1.equals("156") || head1.equals("176")
                || head1.equals("185") || head1.equals("186");
        if (unicomtemp) {
            return UNICOM;
        }
        //unicom 4
        boolean unicomtemp4 = head1.equals("1709");
        if (unicomtemp4) {
            return UNICOM;
        }
        // 电信前3位
        boolean telecomtemp = head1.equals("133") || head1.equals("153")
                || head1.equals("181") || head1.equals("177")
                || head1.equals("180") || head1.equals("189");

        if (telecomtemp) {
            return TELECOM;
        }
        //telecom 4
        boolean telecomtemp4 = head1.equals("1700");
        if (telecomtemp4) {
            return TELECOM;
        }

        return UNKNOWN;
    }
}
