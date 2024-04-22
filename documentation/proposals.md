# PROPOSAL API CURL Commands for Manual Testing or Reference

## Submit a proposal

```bash
curl -X POST "http://localhost:8080/proposals" -H "Content-Type: application/json" -d '{
    "title": "Test Proposal",
    "description": "This is a test proposal.",
    "speakerId": "f47ac10b-58cc-4372-a567-0e02b2c3d479"
}'
```

## Get all proposals

```bash
curl -X GET "http://localhost:8080/proposals" | jq
```

## Get a specific proposal by ID

```bash
curl -X GET "http://localhost:8080/proposals/a2d7610a-f1fc-4f3b-80d5-15b242a62b47" | jq
```