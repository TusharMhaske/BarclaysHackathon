# Start with a base image containing Java runtime
FROM meisterplan/jdk-base:11

# Add Maintainer Info
LABEL maintainer="tushar.mhaske"

# Add a volume pointing to /tmp
VOLUME /tmp
 
# The application's jar file
ARG JAR_FILE=target/BarclaysHack.jar

# Add the application's jar to the container
ADD ${JAR_FILE} BarclaysHack.jar
 
# Run the jar file 
ENTRYPOINT ["java","-jar","/BarclaysHack.jar"]
