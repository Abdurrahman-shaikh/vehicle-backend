Here is the complete content in `.md` format for your `README.md`:

```markdown
# 📦 Switching from MySQL to Apache Derby

## 1. 🔄 Replace MySQL Dependency in `pom.xml`

Remove:

```xml
<!-- MySQL Dependency -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

Add:

```xml
<!-- Derby Dependency -->
<dependency>
    <groupId>org.apache.derby</groupId>
    <artifactId>derby</artifactId>
    <version>10.15.2.0</version>
</dependency>
```

---

## 2. ⚙️ Update `application.properties`

```properties
# Derby (Embedded Mode)
spring.datasource.driver-class-name=org.apache.derby.jdbc.EmbeddedDriver
spring.datasource.url=jdbc:derby:starprotectDB;create=true
spring.datasource.username=
spring.datasource.password=

# Hibernate Dialect for Derby
spring.jpa.database-platform=org.hibernate.dialect.DerbyTenSevenDialect
spring.jpa.hibernate.ddl-auto=update

# Optional SQL logging
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## 3. 🧹 Clean Up MySQL-Specific Config

- Remove MySQL-related scripts like `schema.sql`, `data.sql` if present.
- Replace any MySQL-only data types (like `ENUM`) with standard ones.

---

## 4. 🧱 Entity Compatibility Tips

- Derby stores column names in uppercase. To avoid issues, use:

```java
@Column(name = "your_column_name")
private String yourColumn;
```

- Stick to common field types: `String`, `int`, `LocalDate`, etc.

---

## 5. 🧼 Resetting Derby DB (Optional)

If you want to reset the Derby database:

```bash
rm -rf starprotectDB/
```

---

## 6. 🚀 Using Derby in Client-Server Mode (Optional)

```properties
spring.datasource.driver-class-name=org.apache.derby.jdbc.ClientDriver
spring.datasource.url=jdbc:derby://localhost:1527/starprotectDB;create=true
```

Make sure Derby Network Server is running:

```bash
java -jar derbyrun.jar server start
```
