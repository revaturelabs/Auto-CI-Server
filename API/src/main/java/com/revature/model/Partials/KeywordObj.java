package com.revature.model.Partials;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "keyword" })
public class KeywordObj {
    private String keyword;

    public KeywordObj() {
    }

    public KeywordObj(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public KeywordObj keyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

}
