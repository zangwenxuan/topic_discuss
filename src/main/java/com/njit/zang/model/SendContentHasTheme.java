package com.njit.zang.model;

import lombok.Data;

import java.io.Serializable;

/**
 * send_content_has_theme
 * @author 
 */
@Data
public class SendContentHasTheme implements Serializable {
    private String sendContentFeedId;

    private String themeName;

    private static final long serialVersionUID = 1L;


}