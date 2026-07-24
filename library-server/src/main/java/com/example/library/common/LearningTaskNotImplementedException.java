package com.example.library.common;

public class LearningTaskNotImplementedException extends BusinessException {
    public LearningTaskNotImplementedException(String taskId) {
        super(ErrorCode.NOT_IMPLEMENTED, taskId + " 尚未实现，请参考 docs/requirements.md 完成练习");
    }
}
