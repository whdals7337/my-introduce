import axios from 'axios';

export async function getSkills() {
    const response = await axios.get<Skill[]>(`http://ec2-13-125-104-210.ap-northeast-2.compute.amazonaws.com:8080/api/skill`);
    return response.data;
}

export async function getSkillsByMemberId(memberId: number) {
    const response = await axios.get<Skill[]>(`http://ec2-13-125-104-210.ap-northeast-2.compute.amazonaws.com:8080/api/skill?memberId=${memberId}`);
    return response.data;
}

export async function getSkillById(id: number) {
    const response = await axios.get<Skill>(`http://ec2-13-125-104-210.ap-northeast-2.compute.amazonaws.com:8080/api/skill/${id}`);
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