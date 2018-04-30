package com.xt.mybatis.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xt on 2018/4/30.
 *
 * @Description: 字符串转日期格式 类型绑定
 */
@Component
public class String2DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(s);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
