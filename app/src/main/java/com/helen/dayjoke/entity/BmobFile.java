package com.helen.dayjoke.entity;

/**
 * Created by Helen on 2016/5/9.
 *
 */
public class BmobFile extends BaseEn{
    private String filename = null;
    private String group = null;
    protected String url = null;
    private String __type = "File";

    public BmobFile(String fileName, String group, String url) {
        this.filename = fileName;
        this.group = group;
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String get__type() {
        return __type;
    }

    public void set__type(String __type) {
        this.__type = __type;
    }

    @Override
    public String toString() {
        return "BmobFile{" +
                "filename='" + filename + '\'' +
                ", group='" + group + '\'' +
                ", url='" + url + '\'' +
                ", __type='" + __type + '\'' +
                '}';
    }

   /* public void download(ProgressListener downloadListener){
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new ProgressInterceptor(downloadListener))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                System.out.println(Log.getStackTraceString(e));
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                InputStream is = null;
                FileOutputStream fos = null;
                BufferedInputStream bis = null;
                try {
                    is = response.body().byteStream();
                    File dir = new File(EnvironmentUtil.getCacheFile()+ File.separator +Constant.DOWNLOAD_APK_FILE);
                    if(!dir.exists()){
                        dir.mkdirs();
                    }
                    File file = new File(dir,Constant.DOWNLOAD_APK_NAME);
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    fos = new FileOutputStream(file);
                    bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        fos.flush();
                    }

                }finally {
                    if(fos != null){
                        fos.close();
                    }
                    if(bis != null){
                        bis.close();
                    }
                    if(is != null){
                        is.close();
                    }
                }
            }
        });
    }*/
}
