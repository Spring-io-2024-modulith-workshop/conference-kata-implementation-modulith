# USER API CURL Commands for Manual Testing or Reference

## Create a user

```bash
curl -X POST "http://localhost:8080/users" -H "Content-Type: application/json" -d '{
    "name": "John Doe",
    "role": "SPEAKER"
}'

curl -X POST "http://localhost:8080/users" -H "Content-Type: application/json" -d '{
    "name": "Jane Smith",
    "role": "ATTENDEE"
}'

curl -X POST "http://localhost:8080/users" -H "Content-Type: application/json" -d '{
    "name": "Michael Johnson",
    "role": "ORGANIZER"
}'
curl -X POST "http://localhost:8080/users" -H "Content-Type: application/json" -d '{
    "name": "Emily Davis",
    "role": "SPONSOR"
}'
```

## Get all users

```bash
curl -X GET "http://localhost:8080/users" | jq
```

## Get all users by rol

```bash
curl -X GET "http://localhost:8080/users?role=SPEAKER" | jq
```

## Get a specific user by ID

```bash
curl -X GET "http://localhost:8080/users/1" | jq
```