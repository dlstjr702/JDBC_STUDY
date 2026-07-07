package org.doit.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class EmpDeptSalgradeVO {
	
	private int empno;
	private String ename;
	private LocalDateTime hiredate;
	private Double sal;
	private double pay;
	private String dname;
	private String job;
	private int grade;
	private int deptno;
}
