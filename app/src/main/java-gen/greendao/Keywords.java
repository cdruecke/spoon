package greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table KEYWORDS.
 */
public class Keywords {

    private Long Lang_no;
    private String name;

    public Keywords() {
    }

    public Keywords(Long Lang_no) {
        this.Lang_no = Lang_no;
    }

    public Keywords(Long Lang_no, String name) {
        this.Lang_no = Lang_no;
        this.name = name;
    }

    public Long getLang_no() {
        return Lang_no;
    }

    public void setLang_no(Long Lang_no) {
        this.Lang_no = Lang_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}