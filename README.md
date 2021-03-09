# AWS S3 storage en local con LocalStack

Este proyecto es una demo para un artículo en [dev.to/alextremp](https://dev.to/alextremp)

**Servicio de almacenamiento de ficheros a bucket de S3** 

- Un endpoint POST para enviar un fichero a un path específico.
- El servicio almacenará el fichero a un bucket de S3.
- Devolverá la URL de acceso del fichero, ya sea local, de S3 o de CloudFront si se dispone.


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
