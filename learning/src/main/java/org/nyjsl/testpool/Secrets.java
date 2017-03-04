package org.nyjsl.testpool;

/**
 * Created by pc on 2017/3/3.
 */

public class Secrets {

    private static String CROP_IMG_URL_FMT = "";

    private static String testUrl1 = "http://test.cnhubei.com/mcp/cutter/cnv?l=1&w=240&h=150&q=70&url=http://utest.cnhubei.com/mcp/cutter/cnv?w=710&h=400&q=70&l=1&url=http://media.v.cnhubei.com:85/jcw/upload/Image/default/2016/07/19/5839cc901dd24cf6bb352fa6c8ec03e8/suffix_width_height_2051.jpg";
    private static String testUrl2 = "u=http://test.cnhubei.com/mcp/cutter/cnv?w=710&h=400&q=70&l=1&url=http://media.v.cnhubei.com:85/jcw/upload/Image/default/2016/07/19/5839cc901dd24cf6bb352fa6c8ec03e8/suffix_width_height_2051.jpg";
    private static String testUrl3 = "http://media.v.cnhubei.com:85/jcw/upload/Image/default/2016/07/19/5839cc901dd24cf6bb352fa6c8ec03e8/suffix_width_height_2051.jpg";

    static {
        String API_URL =
//                "http://dev.cnhubei.com/mcp/";
                "http://test.cnhubei.com/mcp/";
//                "http://dx.cnhubei.com/mcp/";
//              "http://10.99.3.128:8080/MCP4DX/";
        CROP_IMG_URL_FMT = API_URL + "cutter/cnv?l=%d&w=%d&h=%d&q=%d&url=%s";
    }
    public static void main(String[] args) {
        testNewCutter(testUrl1);
        testNewCutter(testUrl2);
        testNewCutter(testUrl3);
    }

    private static void testNewCutter(String url){
        if(null != url){
            System.out.println("url = [" + test(url)+ "]");
        }
    }

    private static String test(String url){
        if(null != url){
            if(url.contains("cutter")&& url.contains("url=")){
                url = url.substring(url.lastIndexOf("url=")+4);
            }
        }
        return url;
    }

    /**
     * 拼接裁剪服务的url请求
     * 如果宽高是0 则不裁剪
     * @param w   图片高
     * @param h   图片宽
     * @param p   图片质量，最高100
     * @param url 原始图片地址
     * @return 新图片的url
     */
    public static String getCropImageUrl(int w, int h, int p, String url) {
        if(w>0 && h>0){
            //裁剪
            return String.format(CROP_IMG_URL_FMT,1,w, h, p, url);
        }else{
            //不裁剪
            return String.format(CROP_IMG_URL_FMT,0, w, h, p, url);
        }
    }
}
