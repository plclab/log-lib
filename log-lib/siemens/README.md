# Siemens

TODO

## Limitations

TODO

### S7-1200

- No pointers, there are a few pointer-like data types available (Variant, db_any) but these are too limited
- No SIZEOF()
- Socket communication functions only allow sending/receiving of data starting at array index 0

### S7-300

- UDT cannot be passed from one IN_OUT to another IN_OUT
- No unsigned int data types available
