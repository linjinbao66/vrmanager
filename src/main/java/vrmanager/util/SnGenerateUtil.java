package vrmanager.util;

/**
 * @Classname SnGenerateUtil
 * @Description 随机生成学号
 */
public class SnGenerateUtil {
    public static String generateSn(String clazzId){
        String sn = "";
        sn = "S" + clazzId + System.currentTimeMillis();
        return sn;
    }
    public static String generateTeacherSn(){
        String sn = "";
        sn = "T" + System.currentTimeMillis();
        return sn;
    }
}
