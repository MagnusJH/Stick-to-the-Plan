-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema sticktotheplan
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sticktotheplan
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sticktotheplan` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `sticktotheplan` ;

-- -----------------------------------------------------
-- Table `sticktotheplan`.`cardio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sticktotheplan`.`cardio` (
  `cardioID` INT NOT NULL,
  `rowID` INT NOT NULL,
  `cName` VARCHAR(45) NOT NULL,
  `duration` INT NULL DEFAULT NULL,
  `durType` CHAR(1) NULL DEFAULT NULL,
  `distance` INT NULL DEFAULT NULL,
  `distType` VARCHAR(2) NULL DEFAULT NULL,
  `caloriesBurned` INT NULL DEFAULT NULL,
  PRIMARY KEY (`cardioID`, `rowID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sticktotheplan`.`food`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sticktotheplan`.`food` (
  `foodID` INT NOT NULL,
  `rowID` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `calories` INT NULL DEFAULT NULL,
  `nutrName` VARCHAR(45) NULL DEFAULT NULL,
  `nutrAmount` INT NULL DEFAULT NULL,
  PRIMARY KEY (`foodID`, `rowID`),
  INDEX `food_fk_calendar_idx` (`rowID` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sticktotheplan`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sticktotheplan`.`user` (
  `userName` VARCHAR(20) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`userName`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sticktotheplan`.`weights`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sticktotheplan`.`weights` (
  `weightID` INT NOT NULL,
  `rowID` INT NOT NULL,
  `sets` INT NULL DEFAULT NULL,
  `reps` INT NULL DEFAULT NULL,
  `weight` INT NULL DEFAULT NULL,
  `wName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`weightID`, `rowID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sticktotheplan`.`calendar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sticktotheplan`.`calendar` (
  `userName` VARCHAR(20) NOT NULL,
  `dateID` INT NOT NULL,
  `day` INT NOT NULL,
  `month` CHAR(3) NOT NULL,
  `year` INT NOT NULL,
  `foodTable` INT NOT NULL,
  `weightTable` INT NOT NULL,
  `cardioTable` INT NOT NULL,
  PRIMARY KEY (`userName`, `dateID`),
  INDEX `calendar_fk_food_idx` (`foodTable` ASC) VISIBLE,
  INDEX `calendar_fk_weight_idx` (`weightTable` ASC) VISIBLE,
  INDEX `calendar_fk_cardio_idx` (`cardioTable` ASC) VISIBLE,
  CONSTRAINT `calendar_fk_cardio`
    FOREIGN KEY (`cardioTable`)
    REFERENCES `sticktotheplan`.`cardio` (`cardioID`),
  CONSTRAINT `calendar_fk_food`
    FOREIGN KEY (`foodTable`)
    REFERENCES `sticktotheplan`.`food` (`foodID`),
  CONSTRAINT `calendar_fk_user`
    FOREIGN KEY (`userName`)
    REFERENCES `sticktotheplan`.`user` (`userName`),
  CONSTRAINT `calendar_fk_weight`
    FOREIGN KEY (`weightTable`)
    REFERENCES `sticktotheplan`.`weights` (`weightID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
