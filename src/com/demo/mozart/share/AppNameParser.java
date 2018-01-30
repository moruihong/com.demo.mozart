package com.demo.mozart.share;

import java.io.InputStream;
import java.util.List;

public interface AppNameParser {
    public List<AppName> parse(InputStream is) throws Exception;
}
