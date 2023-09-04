
# Distributor Order System

## Açıklama

Distributor Order System, distribütörlerin siparişlerini takip edebilmeleri için oluşturulmuş bir Spring Boot tabanlı mikroservistir.

## Başlangıç

Bu bölüm, projenin nasıl kurulacağı ve çalıştırılacağı hakkında bilgi içerir.

### Önkoşullar

- Java 17
- Docker (isteğe bağlı)
- MongoDB

### Kurulum

1. Proje klasörüne gidin:

```bash
cd DistributorOrderSystem
```

2. Uygulamayı derleyin:

```bash
./gradlew build
```

3. Uygulamayı çalıştırın:

```bash
java -jar build/libs/<JAR_FILE_NAME>.jar
```

### Docker ile Çalıştırma (Opsiyonel)

1. Docker imajını oluşturun:

```bash
docker build -t distributorordersystem:latest .
```

2. Docker konteynerini çalıştırın:

```bash
docker run -p 8080:8080 distributorordersystem:latest
```

## Kullanım

Uygulama başladıktan sonra, web tarayıcınızda veya API test aracınızda aşağıdaki URL'ye giderek servise erişebilirsiniz:

```
http://localhost:8080
```