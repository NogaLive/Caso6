FROM eclipse-temurin:17-jre
WORKDIR /app
COPY out /app/out
CMD ["java","-cp","/app/out","com.smartbuild.ui.Main"]
