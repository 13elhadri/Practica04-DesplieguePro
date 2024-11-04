# Despliegue Avanzado

## Descripción del Proyecto
Este proyecto implementa una calculadora con funciones básicas:
- **Operaciones**: Suma, resta, multiplicación y división.
- **Código comentado**: El código fuente está documentado para facilitar la comprensión.
- **Pruebas unitarias**: Incluye tests para verificar la funcionalidad de la calculadora.
- **Informe de cobertura**: Generado con Jacoco para visualizar la cobertura de los tests.

## Arquitectura del Despliegue
El despliegue se realiza utilizando Docker para administrar múltiples contenedores, permitiendo una estructura modular para cada función de la aplicación:

1. **Contenedor principal**: Contiene la aplicación de la calculadora. Utiliza un Dockerfile para copiar los archivos de los tests, el Javadoc, y los reportes de Jacoco en volúmenes compartidos.


```dockerfile

   FROM gradle:jdk21-alpine AS build

    WORKDIR /app

    COPY ./gradlew /app/gradlew
    COPY ./gradle /app/gradle
    COPY ./settings.gradle.kts /app/settings.gradle.kts
    COPY ./build.gradle.kts /app/build.gradle.kts
    COPY ./src /app/src 

    RUN ./gradlew build
    RUN ./gradlew javadoc

    FROM eclipse-temurin:21-jre-alpine AS run

    WORKDIR /app

    COPY --from=build /app/build/libs/*.jar /app/my-app.jar
    COPY --from=build /app/build/jacoco /app/jacoco
    COPY --from=build /app/build/reports/tests/test /app/test
    COPY --from=build /app/build/docs/javadoc /app/javadoc

    ENTRYPOINT ["java", "-jar", "/app/my-app.jar"]

```

2. **Contenedores adicionales**:
   - **javadoc_server**: Servidor que accede al volumen compartido donde se encuentran los archivos de documentación generados por Javadoc.
   - **test_server**: Servidor que muestra los resultados de los tests unitarios.
   - **coverage_server**: Servidor que despliega el informe de cobertura de Jacoco.

Cada servidor se conecta a un volumen específico para acceder a sus archivos HTML y mostrar los resultados de la aplicación de forma independiente.


```docker-compose.yml

   services:
    app:
        build:
        context: .
        dockerfile: Dockerfile
        volumes:
        - coverage_data:/app/jacoco  
        - test_data:/app/test 
        - javadoc_data:/app/javadoc

        
    coverage:
        image: ubuntu/nginx
        container_name: coverage_server
        volumes:
        - coverage_data:/var/www/html/
        - ./coverage/sites-available:/etc/nginx/sites-available 
        restart: always
        depends_on:
        - app
        networks:
        - proxy
    

    
    test:
        image: ubuntu/nginx
        container_name: test_server
        volumes:
        - test_data:/var/www/html/
        - ./test/sites-available:/etc/nginx/sites-available 
        restart: always
        depends_on:
        - app
        networks:
        - proxy

    doc:
        image: ubuntu/apache2
        container_name: doc_server
        volumes:
        - javadoc_data:/var/www/html
        - ./doc/sites-available:/etc/apache2/sites-available 
        restart: always
        depends_on:
        - app
        networks:
        - proxy

    appServer:
        image: ubuntu/nginx
        container_name: app_server
        volumes:
        - ./app/website:/var/www/html/
        - ./app/sites-available:/etc/nginx/sites-available 
        restart: always
        networks:
        - proxy

    proxy:
        image: ubuntu/nginx
        container_name: proxy_server
        ports:
        - "80:80"
        - "443:443"
        volumes:
        - ./proxy/conf/nginx.conf:/etc/nginx/nginx.conf
        - ./proxy/certs:/etc/nginx/certs
        restart: always
        depends_on:
        - app
        - doc
        - test
        - coverage
        - appServer
        networks:
        - proxy
    
    networks:
    proxy:

    volumes:
    coverage_data:  
    test_data:
    javadoc_data: 

```


## Proxy Inverso
El proyecto incluye un proxy inverso que permite acceder a los servicios a través de rutas específicas:
- `app.com` - Acceso principal al index con la conexion al resto.
- `app.com/doc` - Acceso a la documentación generada con Javadoc.
- `app.com/test` - Acceso a los resultados de los tests unitarios.
- `app.com/coverage` - Acceso al informe de cobertura de Jacoco.

```nginx.conf

    events {}

    http {
        server {
            listen 80;
            server_name app.com;

            return 301 https://$host$request_uri;
        }

        server {
            listen 443 ssl;
            server_name app.com;

            ssl_certificate /etc/nginx/certs/app.crt;
            ssl_certificate_key /etc/nginx/certs/app.key;
            

            location / {
                proxy_pass http://app_server;
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
            }

            location /doc/ {
                proxy_pass http://doc_server;
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
            }

            location /coverage/ {
                proxy_pass http://coverage_server/;
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
            }


            location /test/ {
                proxy_pass http://test_server/;
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
            }
        }
    }

```

## Simulación en Netlify
Para facilitar el acceso sin depender de Docker, se ha añadido una carpeta `netlify` que contiene los archivos de los volúmenes generados. Esto permite simular el despliegue en Docker cuando se accede a la aplicación en Netlify.

