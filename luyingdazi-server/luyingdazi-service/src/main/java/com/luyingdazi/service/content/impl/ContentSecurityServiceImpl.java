package com.luyingdazi.service.content.impl;

import com.luyingdazi.service.content.ContentSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 内容安全服务实现
 * 使用 DFA（确定性有限自动机）算法实现本地敏感词过滤
 * 后续可对接微信 msgSecCheck API 做二次校验
 *
 * @author luyingdazi
 */
@Slf4j
@Service
public class ContentSecurityServiceImpl implements ContentSecurityService {

    /** DFA 敏感词树 */
    private Map<Character, Object> sensitiveWordMap = new HashMap<>();

    private static final String END_FLAG = "isEnd";

    @PostConstruct
    public void init() {
        loadSensitiveWords();
    }

    @Override
    public boolean checkText(String text, String userId) {
        if (text == null || text.isBlank()) {
            return true;
        }
        // 本地 DFA 检测
        Set<String> found = getSensitiveWords(text);
        if (!found.isEmpty()) {
            log.warn("用户{}内容包含敏感词: {}", userId, found);
            return false;
        }
        // TODO: 后续对接微信 msg_sec_check API
        return true;
    }

    @Override
    public boolean checkImage(String imageUrl, String userId) {
        // TODO: 对接微信 img_sec_check API
        return true;
    }

    @Override
    public String filterText(String text) {
        if (text == null || text.isBlank()) {
            return text;
        }
        StringBuilder result = new StringBuilder(text);
        int length = text.length();

        for (int i = 0; i < length; i++) {
            int matchLength = checkSensitiveWord(text, i);
            if (matchLength > 0) {
                // 替换为 ***
                for (int j = i; j < i + matchLength; j++) {
                    result.setCharAt(j, '*');
                }
                i += matchLength - 1;
            }
        }
        return result.toString();
    }

    // ==================== DFA 算法实现 ====================

    @SuppressWarnings("unchecked")
    private void loadSensitiveWords() {
        Set<String> words = new HashSet<>();
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty() && !line.startsWith("#")) {
                        words.add(line);
                    }
                }
                reader.close();
            }
        } catch (Exception e) {
            log.error("加载敏感词库失败", e);
        }

        // 构建 DFA 树
        for (String word : words) {
            Map<Character, Object> current = sensitiveWordMap;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                Object node = current.get(c);
                if (node == null) {
                    Map<Character, Object> newNode = new HashMap<>();
                    newNode.put(END_FLAG.charAt(0), Boolean.FALSE);
                    current.put(c, newNode);
                    current = newNode;
                } else {
                    current = (Map<Character, Object>) node;
                }
                if (i == word.length() - 1) {
                    current.put(END_FLAG.charAt(0), Boolean.TRUE);
                }
            }
        }
        log.info("敏感词库加载完成，共{}个词", words.size());
    }

    private Set<String> getSensitiveWords(String text) {
        Set<String> result = new HashSet<>();
        for (int i = 0; i < text.length(); i++) {
            int length = checkSensitiveWord(text, i);
            if (length > 0) {
                result.add(text.substring(i, i + length));
                i += length - 1;
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private int checkSensitiveWord(String text, int beginIndex) {
        Map<Character, Object> current = sensitiveWordMap;
        int matchLength = 0;
        boolean found = false;

        for (int i = beginIndex; i < text.length(); i++) {
            char c = text.charAt(i);
            Object node = current.get(c);
            if (node == null) {
                break;
            }
            current = (Map<Character, Object>) node;
            matchLength++;
            if (Boolean.TRUE.equals(current.get(END_FLAG.charAt(0)))) {
                found = true;
                break;
            }
        }
        return found ? matchLength : 0;
    }
}
