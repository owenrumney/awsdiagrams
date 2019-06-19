FROM domblack/oracle-jdk8
# copy WAR into image
COPY www /editor
COPY index.html /index.html
COPY jars/mxPdf.jar /mxPdf.jar
COPY backend/target/backend-1.0-SNAPSHOT-jar-with-dependencies.jar /backend.jar
# run application with this command line 
CMD ["/usr/bin/java", "-jar", "backend.jar", "-cp", "/*"]
