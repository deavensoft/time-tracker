# FROM ringcentral/maven:3.6.3-jdk11.0.7
FROM time-tracker/spring

#
# COPY ./ workspaces/time_tracker
# WORKDIR workspaces/time_tracker
# RUN mvn clean
# RUN mvn package
# ARG JAR_FILE=target/*.jar
# COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","./app.jar","PORT=$PORT","KEYCLOAK_HOST=$KEYCLOAK_HOST","KEYCLOAK_PORT=$KEYCLOAK_PORT","KEYCLOAK_REALM=$KEYCLOAK_REALM","KEYCLOAK_CLIENT=$KEYCLOAK_CLIENT"]