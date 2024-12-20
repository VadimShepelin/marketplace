package com.spring.marketplace.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProductIncorrectData {
    private List<String> info = new ArrayList<String>();

    public void setInfo(String... messages) {
        this.info.addAll(Arrays.asList(messages));
    }

}
