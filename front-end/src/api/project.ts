import axios from 'axios';

export async function getProjects() {
    const response = await axios.get<Project[]>(`/api/project`);
    return response.data;
}

export async function getProjectsByMemberId(memberId: number) {
    const response = await axios.get<Project[]>(`/api/project?memberId=${memberId}`);
    return response.data;
}

export async function getProjectById(id: number) {
    const response = await axios.get<Project>(`/api/project/${id}`);
    return response.data;
}

export type Project =  {
    projectId:         number;
    projectTitle:      string;
    projectContent:    string;
    projectPostScript: string;
    filePath:          string;
    fileOriginName:    string;
    projectLink:       string;
    level:             number;
    memberId:          number;
}