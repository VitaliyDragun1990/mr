package com.revenat.myresume.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "f_skill")
public class Skill extends AbstractEntity<Long> implements ProfileEntity {
	private static final long serialVersionUID = -8068581222220250534L;

	@Id
	@SequenceGenerator(name = "skill_seq_generator", sequenceName = "skill_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skill_seq_generator")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id", nullable = false)
	private Profile profile;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private SkillCategory category;
	
	@Column(nullable = false)
	private String value;
	
	@Override
	public Long getId() {
		return id;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public SkillCategory getCategory() {
		return category;
	}

	public void setCategory(SkillCategory category) {
		this.category = category;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
