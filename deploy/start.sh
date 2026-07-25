#!/bin/bash
# 野趣搭子 - 服务启动脚本
cd /opt/luyingdazi

# 停掉旧进程
PID=$(pgrep -f 'app.jar')
if [ -n "$PID" ]; then
  echo "停止旧进程: $PID"
  kill $PID
  sleep 3
fi

# 启动
nohup java -Xms256m -Xmx384m \
  -jar app.jar \
  --spring.profiles.active=prod \
  --spring.config.additional-location=/opt/luyingdazi/application-prod.yml \
  > app.log 2>&1 &

echo "启动成功, PID: $!"
echo "日志: tail -f /opt/luyingdazi/app.log"
