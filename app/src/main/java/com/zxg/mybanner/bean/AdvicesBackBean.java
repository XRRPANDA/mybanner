package com.zxg.mybanner.bean;

import java.util.List;

/**
 * 作者：zxg on 2016/10/24 19:04
 * 获取广告内容返回bean类
 */
public class AdvicesBackBean extends BaseBean{


    /**
     * id : 6
     * name : 555
     * url : http://192.168.1.118:8888/upload/hbts/7EA38355A4D7436B87FB4C35C882571C.jpg
     * link : http://192.168.1.118:8888/upload/hbts/7EA38355A4D7436B87FB4C35C882571C.jpg
     * createTime : 4164646456
     * creator :
     */

    private List<ContentBean> content;

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "AdvicesBackBean{" +
                "content=" + content +
                "} " + super.toString();
    }

    public static class ContentBean {
        private int id;
        private String name;
        private String url;
        private String link;
        private long createTime;
        private String creator;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        @Override
        public String toString() {
            return "ContentBean{" +
                    "createTime=" + createTime +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", url='" + url + '\'' +
                    ", link='" + link + '\'' +
                    ", creator='" + creator + '\'' +
                    '}';
        }
    }

}
