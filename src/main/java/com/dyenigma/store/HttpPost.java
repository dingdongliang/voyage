package com.dyenigma.store;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Http Post模仿表单上传文件到服务器
 * <p>
 * author dyenigma
 * create 2016/2/26 14:20
 */
public class HttpPost {
    public static String post(String urlStr, Map<String, String> paramsMap, Map<String, String> fileMap) {
        String res = "";
        HttpURLConnection conn = null;
        OutputStream out = null;
        DataInputStream in = null;
        BufferedReader reader = null;

        // boundary就是request头和上传文件内容的分隔符,必须
        String boundary = "---------------------------42832342";
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(50000);
            conn.setReadTimeout(30000);

            //这两个属性上传文件必需
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");

            //设置上传文件的内容类型
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            out = new DataOutputStream(conn.getOutputStream());

            if (paramsMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator iter = paramsMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes());
            }

            if (fileMap != null) {
                Iterator iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();
                    String contentType = new MimetypesFileTypeMap().getContentType(file);
                    if (filename.endsWith(".png")) {
                        contentType = "image/png";
                    }
                    if (contentType == null || contentType.equals("")) {
                        contentType = "application/octet-stream";
                    }

                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");

                    out.write(strBuf.toString().getBytes());

                    in = new DataInputStream(new FileInputStream(file));
                    int bytes;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                }
            }

            byte[] endData = ("\r\n--" + boundary + "--\r\n").getBytes();
            out.write(endData);
            out.flush();

            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + urlStr);
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public static void main(String[] args) {

        String filepath = "E:\\demo.jpg";
        String urlStr = "http://10.3.104.27:8080/dzfp/fpsc/tpfpsc.do";

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("fpdm", "123456789015");
        paramsMap.put("xzfldm1", "dzfpuser_zhls");
        paramsMap.put("fphm", "12345674");
        paramsMap.put("kprq", "2016-02-26");
        paramsMap.put("kpje", "12");
        paramsMap.put("xfmc", "sf");
        paramsMap.put("CZRY_DM", "dzfpuser");

        Map<String, String> fileMap = new HashMap<>();
        fileMap.put("file", filepath);

        String ret = post(urlStr, paramsMap, fileMap);
        System.out.println(ret);
    }
}
