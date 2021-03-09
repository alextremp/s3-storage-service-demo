# AWS S3 storage en local con LocalStack

Este proyecto es la base del post ...

# Uso

El endpoint de ejemplo estará expuesto para POST en `http://localhost:8080/storage`
Acepta `form-data` con:
- `file`: El fichero que queremos guardar en S3
- `path`: La ruta donde queremos guardar el fichero, relativa respecto el bucket

**Ejecución en local contra LocalStack**

```bash
docker-compose up -d
./gradlew bootRun
```

**Ejecución en local contra bucket real**

```bash
# active profile set to production
PROFILE="--spring.profiles.active=pro"
# your AWS access key
ACCESS="--s3-storage.accessKey="
# your AWS secret key
SECRET="--s3-storage.secretKey="
# your AWS S3 bucket
BUCKET="--s3-storage.bucket="
# your AWS CloudFront distro where the bucket is linked as an origin, or empty
CDN="--s3-storage.originFor="
./gradlew bootRun --args="${PROFILE} ${ACCESS} ${SECRET} ${BUCKET} ${CDN}"

```
