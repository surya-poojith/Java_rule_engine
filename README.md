# Visa Rule Evaluation System (Core Java)

## Overview
This project is a config-driven visa rule evaluation system built using Core Java.  
It determines whether a traveler requires a visa based on destination country, passport country, travel purpose, and stay duration.

All visa rules are defined in an external JSON file and loaded at application startup.  
The system validates the configuration strictly and evaluates rules at runtime without using any frameworks or databases.

---

## Project Structure
```
java_rule_engine/
├── Main.java
├── RuleLoader.java
├── RuleRepository.java
├── VisaRuleEvaluator.java
├── VisaRule.java
├── VisaDecision.java
├── InvalidRuleConfigException.java
├── Country.java
├── TravelPurpose.java
├── VisaType.java
├── DocumentType.java
├── visarules.json
└── lib/
└── jackson libraries

```
---

## Components

### Main
Application entry point.  
Loads rules, takes user input from the console, and prints the visa decision.

### RuleLoader
Loads rules from `visarules.json` at startup.  
Validates required fields, enum values, and schema structure.  
Fails fast if configuration is invalid.

### RuleRepository
Stores validated visa rules in memory.  
Provides read-only access to rules.

### VisaRuleEvaluator
Evaluates user input against stored rules.  
Handles valid matches, no rule found, and multiple rule conflicts.

### DTOs and Enums
- VisaRule and VisaDecision are immutable data objects.
- Enums are used for country, travel purpose, visa type, and document type.

---

## Configuration
Visa rules are defined in `visarules.json`.  
Rules include both valid and intentionally invalid entries to test validation and error handling.

---

## Test Scenarios Covered
- Valid rule match
- No rule found
- Multiple rule conflict
- Invalid input values
- Missing or incorrect configuration fields

---

## How to Compile and Run

Compile:
```bash
javac -cp ".;lib/*" *.java
```
Run:
```
java -cp ".;lib/*" Main
```
