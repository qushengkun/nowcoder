package com.langchao.nowcoder.utils;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: SensitiveFilter
 *
 * @Author: qsk
 * @Create: 2023/8/19 - 19:21
 * @version: v1.0
 */
@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    private static final String REPLACEMENT = "***";

    // 根节点
    private TrieNode rootNode = new TrieNode();


    // 初始化前缀树
    public void init(){
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while ((keyword = reader.readLine()) != null) {
                // 添加到前缀树中
                this.addKeyword(keyword);
            }
        }catch (IOException e){
            logger.error("加载敏感文件失败" + e.getMessage());
        }
    }

    // 将一个敏感词添加到前缀树中
    private void addKeyword(String keyword){
        TrieNode tempNode = rootNode;
        for(int i = 0; i < keyword.length(); i++){
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);

            if(subNode == null){
                // 初始化子节点
                subNode = new TrieNode();
                tempNode.addSubNode(c,subNode);
            }
            // 设置结束标识
            if(i == keyword.length()-1){
                tempNode.setKeywordEnd(true);
                break;
            }
            // 指向子节点，进入下一轮循环
            tempNode = subNode;



        }
    }


    /**
     * 过滤敏感词，参数是带过滤的文本，返回的返回后的文本
     * @param text
     * @return
     */
    public String filter(String text){
        if(StringUtils.isBlank(text)){
            return null;
        }

        // 指针1,默认指向 敏感词前缀树 的根节点
        TrieNode tempNode = new TrieNode();
        // 指针2
        int positionBegin = 0;
        // 指针3
        int positionEnd = 0;

        // 结果
        StringBuilder sb = new StringBuilder();

        while(positionBegin < text.length()){
            char c = text.charAt(positionBegin);

            // 如果开始字符是符号
            if(isSymbol(c)){
                // 若指针1 处于根节点,将此符号计入结果，指针2 向下走
                if(tempNode == rootNode){
                    positionBegin += 1;
                    sb.append(c);
                }
                // 如果指针不是根节点
                positionEnd += 1;
                continue;

            }
            // 如果text开始字符不是符号
            tempNode = tempNode.getSubNode(c);
            //
            if(tempNode == null){
                // 以 positionBegin 开头的字符串不是敏感词
                sb.append(text.charAt(positionBegin));
                positionBegin += 1;
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd) {
                // 发现敏感词，将 positionBegin - positionEnd 字符串替换掉
                sb.append(REPLACEMENT);
                positionBegin += 1;
                tempNode = rootNode;
            } else {
                positionEnd += 1;
            }
        }
        // 将最后一批字符计入结果
        sb.append(text.substring(positionBegin));
        return sb.toString();



    }

    // 是否是特殊符号
    private boolean isSymbol(Character c){
        // 0x2E80-0x9FFF 是东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }



    // 内部类 前缀树节点
    private class TrieNode{

        // 关键词结束的标识
        private boolean isKeywordEnd = false;

        // 子节点 （key 是下级字符，value 是下级节点）
        private Map<Character,TrieNode> subNodes = new HashMap<>();


        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        // 添加子节点
        public void addSubNode(Character c,TrieNode node){
            subNodes.put(c,node);
        }

        // 获取子节点的办法
        public TrieNode getSubNode(Character c){
            return subNodes.get(c);
        }
    }


}
