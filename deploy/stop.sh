#!/bin/bash
# 野趣搭子 - 服务停止脚本
PID=$(pgrep -f 'app.jar')
if [ -n "$PID" ]; then
  echo "停止进程: $PID"
  kill $PID
  echo "已停止"
else
  echo "服务未运行"
fi
