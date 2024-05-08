FROM openjdk:17-oracle
VOLUME /tmp
ARG DEPENDENCY=target/dependency
ARG BOOSTAPPDBUSER='user'
ARG BOOSTAPPDBSECRET='password'
ENV BOOSTAPPDBUSER=${BOOSTAPPDBUSER}
ENV BOOSTAPPDBSECRET=${BOOSTAPPDBSECRET}
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.nexergroup.boostapp.java.step.Application"]
