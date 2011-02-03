/*
 * Copyright 2011 Michael Bedward
 * 
 * This file is part of jai-tools.
 *
 * jai-tools is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * jai-tools is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with jai-tools.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
  
 /** 
  * @author Michael Bedward
  */

tree grammar TagConstants;

options {
    tokenVocab = Jiffle;
    ASTLabelType = CommonTree;
    output = AST;
    filter = true;
}


@header {
package jaitools.jiffle.parser;
}

topdown         : id
                | constant
                ;

id              : ID
                  -> {ConstantLookup.isDefined($ID.text)}? CONSTANT[$ID.text]
                  -> ID
                ;

constant        : TRUE -> FLOAT_LITERAL["1.0"]
                | FALSE -> FLOAT_LITERAL["0.0"]
                | NULL -> CONSTANT["NaN"]
                ;