import axios from 'axios';

export async function getSkills() {
    const response = await axios.get<Skill[]>(`http://localhost:8080/api/skill`);
    return response.data;
}

export async function getSkillsByMemberId(memberId: number) {
    const response = await axios.get<Skill[]>(`http://localhost:8080/api/skill?memberId=${memberId}`);
    return response.data;
}

export async function getSkillById(id: number) {
    const response = await axios.get<Skill>(`http://localhost:8080/api/skill/${id}`);
    return response.data;
}

export type Skill = {
    skillId:        number;
    skillName:      string;
    filePath:       string;
    fileOriginName: string;
    skillLevel:     number;
    level:          number;
    memberId:       number;
}