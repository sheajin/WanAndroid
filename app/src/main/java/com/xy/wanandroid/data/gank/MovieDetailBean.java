package com.xy.wanandroid.data.gank;

import java.util.List;

/**
 * Created by jxy on 2018/8/14.
 */

public class MovieDetailBean {

    /**
     * rating : {"max":10,"average":5.2,"stars":"25","min":0}
     * reviews_count : 137
     * wish_count : 26860
     * douban_site :
     * year : 2018
     * images : {"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2537399580.jpg","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2537399580.jpg","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2537399580.jpg"}
     * alt : https://movie.douban.com/subject/25917789/
     * id : 25917789
     * mobile_url : https://movie.douban.com/subject/25917789/mobile
     * title : 铁血战士
     * do_count : null
     * share_url : https://m.douban.com/movie/subject/25917789
     * seasons_count : null
     * schedule_url : https://movie.douban.com/subject/25917789/cinema/
     * episodes_count : null
     * countries : ["美国","加拿大"]
     * genres : ["动作","科幻","惊悚"]
     * collect_count : 23757
     * casts : [{"alt":"https://movie.douban.com/celebrity/1053504/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1473866362.76.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1473866362.76.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1473866362.76.jpg"},"name":"波伊德·霍布鲁克","id":"1053504"},{"alt":"https://movie.douban.com/celebrity/1340760/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1488883883.7.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1488883883.7.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1488883883.7.jpg"},"name":"崔凡特·罗兹","id":"1340760"},{"alt":"https://movie.douban.com/celebrity/1332866/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1456737567.18.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1456737567.18.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1456737567.18.jpg"},"name":"雅各布·特伦布莱","id":"1332866"},{"alt":"https://movie.douban.com/celebrity/1000430/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1433487259.19.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1433487259.19.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1433487259.19.jpg"},"name":"科甘-迈克尔·凯","id":"1000430"}]
     * current_season : null
     * original_title : The Predator
     * summary : 一艘宇宙飞船坠落地球，正在执行军事任务的狙击手奎因·麦肯纳（波伊德·霍布鲁克 饰演）恰好遭遇外星人并发生激战，侥幸逃脱的他捡到了外星人的装备并寄回了家想再做他用。传说这种被称为铁血战士的外星人曾数次光临地球，使用各种残忍的手段对人类进行猎杀游戏。而这一次，奎因的儿子罗里收到包裹后意外触发机关，引来了更强大的终极铁血士降临地球。而想要阻止其大开杀戒的却是一群问题士兵和一位生物科学家凯茜·布拉克（奥立薇娅·玛恩 饰演）。事情却远不像他们以为的那样简单，一场铁血、终极铁血与人类之间的三方猎杀大战正式拉响！
     * subtype : movie
     * directors : [{"alt":"https://movie.douban.com/celebrity/1036569/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p21117.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p21117.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p21117.jpg"},"name":"沙恩·布莱克","id":"1036569"}]
     * comments_count : 5268
     * ratings_count : 12524
     * aka : ["铁血战士新续集","终极战士：掠夺者(台)","铁血战士：血兽进化(港)","铁血战士4","Predator 4"]
     */

    private RatingBean rating;
    private int reviews_count;
    private int wish_count;
    private String douban_site;
    private String year;
    private ImagesBean images;
    private String alt;
    private String id;
    private String mobile_url;
    private String title;
    private Object do_count;
    private String share_url;
    private Object seasons_count;
    private String schedule_url;
    private Object episodes_count;
    private int collect_count;
    private Object current_season;
    private String original_title;
    private String summary;
    private String subtype;
    private int comments_count;
    private int ratings_count;
    private List<String> countries;
    private List<String> genres;
    private List<CastsBean> casts;
    private List<DirectorsBean> directors;
    private List<String> aka;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public int getReviews_count() {
        return reviews_count;
    }

    public void setReviews_count(int reviews_count) {
        this.reviews_count = reviews_count;
    }

    public int getWish_count() {
        return wish_count;
    }

    public void setWish_count(int wish_count) {
        this.wish_count = wish_count;
    }

    public String getDouban_site() {
        return douban_site;
    }

    public void setDouban_site(String douban_site) {
        this.douban_site = douban_site;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getDo_count() {
        return do_count;
    }

    public void setDo_count(Object do_count) {
        this.do_count = do_count;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public Object getSeasons_count() {
        return seasons_count;
    }

    public void setSeasons_count(Object seasons_count) {
        this.seasons_count = seasons_count;
    }

    public String getSchedule_url() {
        return schedule_url;
    }

    public void setSchedule_url(String schedule_url) {
        this.schedule_url = schedule_url;
    }

    public Object getEpisodes_count() {
        return episodes_count;
    }

    public void setEpisodes_count(Object episodes_count) {
        this.episodes_count = episodes_count;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public Object getCurrent_season() {
        return current_season;
    }

    public void setCurrent_season(Object current_season) {
        this.current_season = current_season;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(int ratings_count) {
        this.ratings_count = ratings_count;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<CastsBean> getCasts() {
        return casts;
    }

    public void setCasts(List<CastsBean> casts) {
        this.casts = casts;
    }

    public List<DirectorsBean> getDirectors() {
        return directors;
    }

    public void setDirectors(List<DirectorsBean> directors) {
        this.directors = directors;
    }

    public List<String> getAka() {
        return aka;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    public static class RatingBean {
        /**
         * max : 10
         * average : 5.2
         * stars : 25
         * min : 0
         */

        private int max;
        private double average;
        private String stars;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public double getAverage() {
            return average;
        }

        public void setAverage(double average) {
            this.average = average;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    public static class ImagesBean {
        /**
         * small : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2537399580.jpg
         * large : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2537399580.jpg
         * medium : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2537399580.jpg
         */

        private String small;
        private String large;
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public static class CastsBean {
        /**
         * alt : https://movie.douban.com/celebrity/1053504/
         * avatars : {"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1473866362.76.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1473866362.76.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1473866362.76.jpg"}
         * name : 波伊德·霍布鲁克
         * id : 1053504
         */

        private String alt;
        private AvatarsBean avatars;
        private String name;
        private String id;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public AvatarsBean getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsBean avatars) {
            this.avatars = avatars;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AvatarsBean {
            /**
             * small : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1473866362.76.jpg
             * large : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1473866362.76.jpg
             * medium : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1473866362.76.jpg
             */

            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }
    }

    public static class DirectorsBean {
        /**
         * alt : https://movie.douban.com/celebrity/1036569/
         * avatars : {"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p21117.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p21117.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p21117.jpg"}
         * name : 沙恩·布莱克
         * id : 1036569
         */

        private String alt;
        private AvatarsBeanX avatars;
        private String name;
        private String id;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public AvatarsBeanX getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsBeanX avatars) {
            this.avatars = avatars;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AvatarsBeanX {
            /**
             * small : https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p21117.jpg
             * large : https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p21117.jpg
             * medium : https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p21117.jpg
             */

            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }
    }
}
