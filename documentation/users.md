# USER API CURL Commands for Manual Testing or Reference

## Create a user

```bash
curl -X POST "http://localhost:8080/users" -H "Content-Type: application/json" -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "(555) 555-5551",
    "role": "SPEAKER"
}'

curl -X POST "http://localhost:8080/users" -H "Content-Type: application/json" -d '{
    "name": "Jane Smith",
    "email": "jane@example.com",
    "phone": "(555) 555-5552",
    "role": "ATTENDEE"
}'

curl -X POST "http://localhost:8080/users" -H "Content-Type: application/json" -d '{
    "name": "Michael Johnson",
    "email": "michael@example.com",
    "phone": "(555) 555-5553",
    "role": "ORGANIZER"
}'
curl -X POST "http://localhost:8080/users" -H "Content-Type: application/json" -d '{
    "name": "Emily Davis",
    "email": "emily@example.com",
    "phone": "(555) 555-5554",
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