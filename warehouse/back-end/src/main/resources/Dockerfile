FROM openjdk:17
# 指定容器工作目录为 apps
WORKDIR /class
EXPOSE 9090
# 将宿主机上的 ./user-1.0-SNAPSHOT.jar 文件拷贝到容器的工作目录下，并改名为 user.jar
COPY ./back-end-0.0.1-SNAPSHOT.jar ./warehouse.jar
# --spring.profiles.active=prod 表示按照 application-prod.yml 配置文件加载
ENTRYPOINT ["java", "-jar", "warehouse.jar"]
