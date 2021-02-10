FROM nginx

COPY ./nginx/nginx.conf /etc/nginx/nginx.conf

WORKDIR /usr/share/nginx/html
ADD ./build ./

EXPOSE 93