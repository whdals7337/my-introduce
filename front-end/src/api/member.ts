import axios from 'axios';

export async function getMembers() {
    const response = await axios.get<Member[]>(`/api/member`);
    return response.data;
}

export async function getMemberById(id: number) {
    const response = await axios.get<Member>(`/api/member/${id}`);
    return response.data;
}

export type Member = {
    memberId:        number;
    comment:         string;
    filePath:        string;
    fileOriginName:  string;
    subIntroduction: string;
    introduction:    string;
    phoneNumber:     string;
    email:           string;
}