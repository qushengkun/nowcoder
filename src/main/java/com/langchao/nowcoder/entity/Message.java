package com.langchao.nowcoder.entity;

import java.util.Date;

/**
 * Description: Message
 *
 * @Author: qsk
 * @Create: 2023/8/21 - 19:36
 * @version: v1.0
 */
public class Message {

    private int id;
    private int fromId;
    private int toId;
    private String conversationId;
    // 0:未读 1已读 2删除
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", conversationId='" + conversationId + '\'' +
                ", status=" + status +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private String content;
    private Date createTime;

}
