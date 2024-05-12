# Tickets API CURL Commands for Manual Testing or Reference

## Get all tickets

```bash
curl -X GET "http://localhost:8080/tickets" \
-H "Accept: application/json" | jq
```

## Get a single ticket

```bash
curl -X GET "http://localhost:8080/tickets/84b5b680-cefc-4e0a-bace-55c679066c23" \
-H "Accept: application/json" | jq
```

## Buy a ticket

```bash
curl -X POST "http://localhost:8080/tickets/buy" \
    -H "Accept: application/json" \
    -H "Content-Type: application/json" \
    -d '{
          "category": "REGULAR",
          "date": "2024-04-24",
          "price": 150,
          "status": "PENDING"
        }' | jq
```

## Book a ticket

```bash
curl -X POST "http://localhost:8080/tickets/book" \
    -H "Content-Type: application/json" \
    -d '{
          "category": "BLIND",
          "price": 200,
          "status": "PENDING"
        }' | jq
```

## Cancel a ticket

```bash
curl -X PATCH "http://localhost:8080/tickets/84b5b680-cefc-4e0a-bace-55c679066c23/cancel" \
-H "Content-Type: application/x-www-form-urlencoded" \
-H "Accept: application/json" | jq
```

## Confirm a ticket

```bash
curl -X PATCH "http://localhost:8080/tickets/b605c861-4514-42ef-884d-0e136636586c/confirm" \
-H "Content-Type: application/x-www-form-urlencoded" \
-H "Accept: application/json" | jq
```
