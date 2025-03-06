# LG Follow

![KakaoTalk_20241206_001420615](https://github.com/user-attachments/assets/48efc328-af90-45d6-ad90-d0bff8b0a05c)

## 📗 Proposal

We are introducing technology that allows sound to follow the user, creating an environment where they can hear audio in any part of the house with LG appliances equipped with speakers.

Imagine an office worker getting ready for work in the morning, listening to music or the news through a speaker. During the morning routine, they might wash up in the bathroom, make coffee in the kitchen, have breakfast, choose clothes from the closet, and get dressed. For someone who moves between rooms so frequently, it's almost impossible to catch 100% of the audio output from a stationary speaker.

We will use a Raspberry Pi and PIR sensors to detect the user's location. The location information will be sent to a the speaker. For instance, if they leave the living room and enter the bedroom, the speaker in the living room will stop, and the speaker in the bedroom will automatically take over, seamlessly continuing the audio experience.

Additionally,  we provide an app that turns children's drawings into songs using generative AI. When a child draws a picture, the AI will create a song based on their artwork. With LG Follow, kids can enjoy listening to their own music as they move around the house, making each moment truly unique and magical.

Through generative AI, the drawings will be transformed into prompts, and those prompts will be turned into music.

## 📐 Architecture Design 

![a](https://github.com/user-attachments/assets/27857389-f0d5-430f-9c42-670e23e16516)


---

## 📌 Tech Blog

**[Notion link](https://bit.ly/LG-Follow)**

---

## 🖥️ AI_Purpose

Our team has developed a multimodal deep learning model based on the BLIP architecture to generate prompts from images. BLIP is a model that connects images and text, making it possible to create descriptive and meaningful prompts from images. 

To enhance the accuracy of BLIP and produce richer prompts, we fine-tuned the model using the Flickr30k dataset via the Hugging Face framework. Flickr30k is a comprehensive dataset containing everyday images with detailed annotations. 

This fine-tuning process has improved the model's ability to interpret and describe various types of visual input, including real-world images and hand-drawn illustrations.


## 🖥️ AI_Functionality

The model takes an input image and generates a descriptive prompt based on its visual features. This functionality allows the user to easily extract meaningful textual descriptions from various types of images, such as real-world photos or hand-drawn sketches. 

The model was trained using the Flickr30k dataset, enabling it to recognize and describe a wide range of visual elements effectively.


## 🖥️ AI_Table

![AI_Table](https://github.com/user-attachments/assets/89435f7b-9c54-4190-b484-65a5e69b3d19)
---

- **BLIP (Bootstrapping Language-Image Pre-training)**
  - The BLIP model is a multimodal model designed to connect images and text. It is capable of generating descriptive text prompts based on the visual features of input images.
  - The model is used to analyze input images and generate relevant text prompts that describe the visual content. This is especially useful for tasks involving image captioning or generating textual descriptions from images.

- **Flickr30K dataset**
  - The Flickr30K dataset is a comprehensive collection of everyday images, each annotated with detailed captions. This dataset contains 30,000 images and associated text descriptions, providing a wide variety of visual contexts.
  - The dataset was used to fine-tune the BLIP model, significantly improving its accuracy and enhancing its ability to generate richer and more contextually accurate text prompts based on visual elements.

- **PyTorch**: PyTorch was used as the framework for implementing and training the deep learning model. Its integration with Hugging Face facilitated the fine-tuning process with the Flickr30K dataset.

- **Training Details**
  - Epochs: The model was trained on the entire dataset for 3 epochs. This allows the model to sufficiently learn the patterns in the data and improve its generalization capabilities.
  - Time per Epoch: Each epoch took approximately 5 hours to complete, reflecting the model's complexity and the large size of the dataset.
  - Effect: These training configurations helped the model improve its ability to understand fine-grained visual details and convert them into meaningful text descriptions.

- **System Configuration**
  - CPU: Intel Core i5 processor
  - RAM: 32GB, enabling efficient processing of large datasets and fast training times.


## 🖥️ Datasets

[**Flickr30k dataset**](https://huggingface.co/datasets/nlphuji/flickr30k)

### About the Dataset
The Flickr30k dataset is a comprehensive collection of images with corresponding textual descriptions, designed to facilitate research and development in the field of computer vision and natural language processing. This dataset is widely used for tasks such as:

- Image Captioning
- Visual Question Answering
- Multimodal Learning

By linking visual data with descriptive text, it serves as a robust resource for understanding and modeling the relationship between images and language.

---

### Data Overview

**The dataset contains**:
- 30,000 images: Each image is paired with five unique textual descriptions.
- Text descriptions: Focused on actions, objects, and scenes, providing a detailed narrative for each image.

 **Features**:
- Image: The visual data in JPEG format.
- Captions: A list of five captions describing the corresponding image.

[Original paper](https://aclanthology.org/Q14-1006.pdf)

### Location of Source Code

**[Github link](https://github.com/LG-Follow/AI)**

---

## 🧑‍🤝‍🧑 Members

| Name        | Organization                         | E-mail                        |
|-------------|-------------------------------|-------------------------------|
| **Gyudong Kim** | Dept. of Information Systems | [gyudong1594@hanyang.ac.kr](mailto:gyudong1594@hanyang.ac.kr) |
| **Mingeun Kim** | Dept. of Information Systems | [alsrms0206@hanyang.ac.kr](mailto:alsrms0206@hanyang.ac.kr) |
| **Taegeon Park** | Dept. of Information Systems | [qkrxorjs@hanyang.ac.kr](mailto:qkrxorjs@hanyang.ac.kr) |
| **Mingyu Jeong** | Dept. of Information Systems | [alsrb595@hanyang.ac.kr](mailto:alsrb595@hanyang.ac.kr) |

