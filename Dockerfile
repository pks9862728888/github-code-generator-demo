FROM maven:3.6.1-jdk-11

# Copy google-chrome in docker image
# Latest version download link: https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
COPY deploy/chromebinaries/google-chrome-stable_current_amd64.deb .
RUN apt-get update -y && \
    apt-get install -y --allow-unauthenticated ./google-chrome-stable_current_amd64.deb

# Chrome-driver download link: https://chromedriver.storage.googleapis.com/100.0.4896.20/chromedriver_linux64.zip \
COPY deploy/chromebinaries/chromedriver_linux64.zip .
RUN unzip chromedriver_linux64.zip && mv chromedriver /usr/local/bin
