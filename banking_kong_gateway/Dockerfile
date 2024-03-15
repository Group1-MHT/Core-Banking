FROM kong/kong-gateway:2.8.4.7-alpine
USER root
RUN apk add --no-cache unzip
RUN luarocks install kong-plugin-sync-eureka 
EXPOSE 8000 8443 8001 8444
CMD ["kong", "docker-start"]
